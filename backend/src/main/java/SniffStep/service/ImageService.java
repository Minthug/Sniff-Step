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
public class ImageService implements FileService {

    private final AmazonS3 amazonS3;
    private final ImageRepository imageRepository;

    private String location = "/Users/minthug/Destop/image/";

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public void upload(MultipartFile file, String filename) {
        try {
            file.transferTo(new File(location + filename));
        } catch (IOException e) {
            throw new BusinessLogicException(ExceptionCode.FILE_TYPE_NOT_ACCEPTED);
        }
    }

    @Override
    public void delete(String filename) {
        new File(location + filename).delete();
    }
}
