package SniffStep.common.utils;

import SniffStep.common.exception.UnsupportedFileTypeException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class FileUtils {

    private static final String BASE_DIRECTORY = "image";

    public static String getRandomFileName() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getFilePath(MultipartFile file, String fileName) {
        String extension = StringUtils.getFilenameExtension(Objects.requireNonNull(file.getOriginalFilename()));

        if (!isValidFileType(extension)) {
            throw new UnsupportedFileTypeException(extension + " is not supported file type");
        }
        return BASE_DIRECTORY + "/" + fileName + "." + extension;
    }

    private static boolean isValidFileType(String extension) {
        return Arrays.stream(FileType.values())
                .anyMatch(type -> type.getExtension().equals(extension));
    }
}
