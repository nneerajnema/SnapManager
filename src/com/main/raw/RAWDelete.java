package com.main.raw;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RAWDelete {

    /**
     * @param args
     */
    public static void main(String[] args) {

        /* Base dir of all pics */
        String baseDir = "C:\\Neeraj\\Pics\\";

        /* Pic Dir */
        String eventFolder = "Ghughra";

        String rawFolder = "RAW";

        /* Directory containing JPEG files */
        String jpegFilesPath = baseDir + eventFolder;

        /* Directory containing RAW files */
        String rawFilesPath = jpegFilesPath + "/" + rawFolder;

        if (delete(rawFilesPath, jpegFilesPath)) {
            System.out.println("***SUCCESS***");
        } else {
            System.out.println("!!!FAILURE!!!");
        }

    }

    /**
     * Method deletes the RAW images for which the corresponding JPEG is not found.
     *
     * @param rawFilesPath  Directory where RAW files are stored
     * @param jpegFilesPath Directory where JPEG files are stored
     * @return Result of delete operation
     */
    private static boolean delete(String rawFilesPath, String jpegFilesPath) {
        boolean isSuccess = true;

        File rawContainer = new File(rawFilesPath);
        File jpegContainer = new File(jpegFilesPath);

        if (rawContainer.isDirectory() && jpegContainer.isDirectory()) {
            List<String> jpegImages = getJpegFileNames(jpegContainer);
            Arrays.asList(rawContainer.listFiles()).stream()
                    .filter(rawImage -> rawImage.isFile() && (!jpegImages.contains(truncateExtension(rawImage))))
                    .forEach(rawImage -> System.out.println(rawImage.getName() + " deletion " + (rawImage.delete() ? "complete." : "failed.")));
        } else {
            isSuccess = false;
        }

        return isSuccess;
    }

    /**
     * Method returns the list of JPEG file name without their extension
     *
     * @param jpegContainer The folder which contains the JPEG files
     * @return List of JPEG file names without extension
     */
    private static List<String> getJpegFileNames(File jpegContainer) {
        List<String> jpegFiles = new ArrayList<String>();
        if (jpegContainer.isDirectory()) {
            jpegFiles = Arrays.asList(jpegContainer.listFiles()).stream().
                    filter(jpegFile -> jpegFile.isFile()).map(jpegFile -> truncateExtension(jpegFile)).
                    collect(Collectors.toList());
        }

        return jpegFiles;
    }

    /**
     * Strips the filename of its extension
     *
     * @param file File whose name has to be extracted
     * @return Filename without extension
     */
    private static String truncateExtension(File file) {
        return file.getName().substring(0, file.getName().lastIndexOf("."));
    }

}
