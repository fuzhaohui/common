/*
 *  Copyright 2007-2008, Plutext Pty Ltd.
 *   
 *  This file is part of docx4j.

    docx4j is licensed under the Apache License, Version 2.0 (the "License"); 
    you may not use this file except in compliance with the License. 

    You may obtain a copy of the License at 

        http://www.apache.org/licenses/LICENSE-2.0 

    Unless required by applicable law or agreed to in writing, software 
    distributed under the License is distributed on an "AS IS" BASIS, 
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
    See the License for the specific language governing permissions and 
    limitations under the License.

 */


package org.docx4j.samples;

import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;

import java.io.File;

/**
 * Creates a WordprocessingML document from scratch.
 *
 * @author Jason Harrop
 * @version 1.0
 */
public class AddImage {

    public static void main(String[] args) throws Exception {

        System.out.println("Creating package..");
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();


        File file = new File("D:\\AU-wp1.jpg");
        //File file = new File("C:\\Documents and Settings\\Jason Harrop\\My Documents\\LANL\\fig1.pdf" );

        // Our utility method wants that as a byte array

        java.io.InputStream is = new java.io.FileInputStream(file);
        long length = file.length();
        // You cannot create an array using a long type.
        // It needs to be an int type.
        if (length > Integer.MAX_VALUE) {
            System.out.println("File too large!!");
        }
        byte[] bytes = new byte[(int) length];
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            System.out.println("Could not completely read file " + file.getName());
        }
        is.close();

        String filenameHint = null;
        String altText = null;
        int id1 = 0;
        int id2 = 1;

        org.docx4j.wml.P p = newImage(wordMLPackage, bytes,
                filenameHint, altText,
                id1, id2);

        // Now add our p to the document
        wordMLPackage.getMainDocumentPart().addObject(p);

        org.docx4j.wml.P p2 = newImage(wordMLPackage, bytes,
                filenameHint, altText,
                id1, id2, 3000);

        // Now add our p to the document
        wordMLPackage.getMainDocumentPart().addObject(p2);

        org.docx4j.wml.P p3 = newImage(wordMLPackage, bytes,
                filenameHint, altText,
                id1, id2, 6000);

        // Now add our p to the document
        wordMLPackage.getMainDocumentPart().addObject(p3);


        // Now save it
        wordMLPackage.save(new java.io.File("d:/result.docx"));

        System.out.println("Done.");

    }

    public static org.docx4j.wml.P newImage(WordprocessingMLPackage wordMLPackage,
                                            byte[] bytes,
                                            String filenameHint, String altText,
                                            int id1, int id2) throws Exception {

        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);

        Inline inline = imagePart.createImageInline(filenameHint, altText,
                id1, id2);

        // Now add the inline in w:p/w:r/w:drawing
        org.docx4j.wml.ObjectFactory factory = new org.docx4j.wml.ObjectFactory();
        org.docx4j.wml.P p = factory.createP();
        org.docx4j.wml.R run = factory.createR();
        p.getParagraphContent().add(run);
        org.docx4j.wml.Drawing drawing = factory.createDrawing();
        run.getRunContent().add(drawing);
        drawing.getAnchorOrInline().add(inline);

        return p;

    }

    public static org.docx4j.wml.P newImage(WordprocessingMLPackage wordMLPackage,
                                            byte[] bytes,
                                            String filenameHint, String altText,
                                            int id1, int id2, long cx) throws Exception {

        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);

        Inline inline = imagePart.createImageInline(filenameHint, altText,
                id1, id2, cx);

        // Now add the inline in w:p/w:r/w:drawing
        org.docx4j.wml.ObjectFactory factory = new org.docx4j.wml.ObjectFactory();
        org.docx4j.wml.P p = factory.createP();
        org.docx4j.wml.R run = factory.createR();
        p.getParagraphContent().add(run);
        org.docx4j.wml.Drawing drawing = factory.createDrawing();
        run.getRunContent().add(drawing);
        drawing.getAnchorOrInline().add(inline);

        return p;
    }
}
