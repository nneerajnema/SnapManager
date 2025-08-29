package com.main.raw;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/*
A Java8 based project to delete irrelevant RAW files after a shoot when corresponding JPEG files have been deleted.

The code needs three variables to start with:
baseDir: Base directory containing all event specific folders
eventFolder: Directory inside <baseDir> of the specific event, e.g., Birthday Celebration. The directory houses all JPEG files imported from camera memory.
rawFolder: Directory which houses all RAW files. It may be RAW / NRW / CR2/ etc.

Windows example
C:\
└───Pics
    └───Birthday 2021
        └───RAW

Based on the above directory structure, the value of variables will be:
--> baseDir: C:/Pics/
--> eventFolder: Birthday 2021
--> rawFolder: RAW

*/
public class JpegDelete {

    /**
     * @param args No Args
     */
    public static void main(String[] args) {

        /* Base dir of all pics */
        String baseDir = "";

        /* Pic dir */
        String eventFolder = "";

        /* RAW folder name */
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
     * Method deletes the JPEG images for which the corresponding JPEG is not found.
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
            List<String> rawImages = getRawFileNames(rawContainer);
            Arrays.stream(Objects.requireNonNull(jpegContainer.listFiles()))
                    .filter(jpegImage -> jpegImage.isFile() && (!rawImages.contains(truncateExtension(jpegImage))))
                    .forEach(jpegImage -> System.out.println(jpegImage.getName() + " deletion " + (jpegImage.delete() ? "complete." : "failed.")));
        } else {
            isSuccess = false;
        }

        return isSuccess;
    }




    /**
     * Method returns the list of RAW file name without their extension
     *
     * @param rawContainer The folder which contains the RAW files
     * @return List of RAW file names without extension
     */
    private static List<String> getRawFileNames(File rawContainer) {
        List<String> rawFiles = new ArrayList<>();
        if (rawContainer.isDirectory()) {
            rawFiles = Arrays.stream(Objects.requireNonNull(rawContainer.listFiles())).
                    filter(File::isFile).map(JpegDelete::truncateExtension).
                    collect(Collectors.toList());
        }

        return rawFiles;
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
