package SniffStep.service;

import SniffStep.common.exception.BusinessLogicException;
import SniffStep.common.exception.ExceptionCode;
import SniffStep.dto.ImageResponseDTO;
import SniffStep.entity.Board;
import SniffStep.entity.Image;
import SniffStep.repository.ImageRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final AmazonS3 amazonS3;
    private final ImageRepository imageRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<ImageResponseDTO> addFile(List<MultipartFile> multipartFiles) throws IOException {
        List<ImageResponseDTO> imageResponseList = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            ImageResponseDTO imageResponseDTO = saveFile(multipartFile);

            imageResponseList.add(imageResponseDTO);
        }
        return imageResponseList;
    }

    public List<String> saveBoard(Board saveBoard, List<Long> imageId) {
        List<String> saveImageUrlList = new ArrayList<>();

        for (Long id : imageId) {
            Image image = findById(id);
            image.updatePost(saveBoard);
            imageRepository.save(image);
        }
        return saveImageUrlList;
    }



    private Image findById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 이미지가 없습니다. id = " + id));
    }

    public String findUrlById(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 이미지가 없습니다. id = " + id));

        return image.getS3Url();
    }

    public List<String> findUrlByBoardId(Long id) {
        List<Image> imgList = imageRepository.findByBoardId(id);
        List<String> postImageUrlList = imgList.stream()
                .map(i -> i.getS3Url()).collect(Collectors.toList());

        return postImageUrlList;
    }

    public String findThmUrlByBoardId(Long id) {
        if (imageRepository.findByBoardId(id).size() != 0) {
            Image image = imageRepository.findByBoardId(id).get(0);
            String thumbnailImage = image.getS3Url();
            return thumbnailImage;
        }
        return "존재하지 않는 게시글 입니다.";
    }

    public void deleteFile(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 이미지가 없습니다. id = " + id));

        String bucketKey = image.getS3FilePath();
        amazonS3.deleteObject(bucket, bucketKey);
        imageRepository.delete(image);
    }

    public ImageResponseDTO saveFile(MultipartFile multipartFile) throws IOException {

        String absolutePath = new File("").getAbsolutePath() + File.separator + "temp";
        String fileName = "";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String currentDate = simpleDateFormat.format(new Date());

        String contentType = multipartFile.getContentType();

        if (!ObjectUtils.isEmpty(contentType)) {
            if (verifyContentType(contentType)) {
                fileName = UUID.randomUUID() + multipartFile.getOriginalFilename();

                // save local
                File file = new File(absolutePath + File.separator + fileName);
                if (!file.exists()) { file.mkdirs(); }
                    multipartFile.transferTo(file);
                    file.createNewFile();

                    // save S3
                    String bucketKey = fileName;
                    amazonS3.putObject(new PutObjectRequest(bucket, bucketKey, file).withCannedAcl(CannedAccessControlList.PublicRead));

                    // Image Save
                    String s3Url = amazonS3.getUrl(bucket, bucketKey).toString();

                    Image img = Image.builder()
                            .name(fileName)
                            .s3Url(s3Url)
                            .s3FilePath(bucketKey)
                            .build();

                    imageRepository.save(img);
                    ImageResponseDTO imageResponseDTO = new ImageResponseDTO(img);
                    file.delete();

                    return imageResponseDTO;

                } else {
                    throw new BusinessLogicException(ExceptionCode.FILE_TYPE_NOT_ACCEPTED);
                }
            } else {
                throw new BusinessLogicException(ExceptionCode.FILE_TYPE_NOT_ACCEPTED);
            }
        }


    public boolean verifyContentType(String contentType) {
        if (contentType.contains("image/jpg") || contentType.contains("image/jpeg") || contentType.contains("image/png") || contentType.contains("image/gif")) {
            return true;
        } else {
            return false;
        }
    }
}
