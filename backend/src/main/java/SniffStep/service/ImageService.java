package SniffStep.service;

import SniffStep.common.exception.BusinessLogicException;
import SniffStep.common.exception.ExceptionCode;
import SniffStep.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService implements FileService {

    private final ImageRepository imageRepository;

    private String location = "/Users/minthug/Desktop/image/";

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
