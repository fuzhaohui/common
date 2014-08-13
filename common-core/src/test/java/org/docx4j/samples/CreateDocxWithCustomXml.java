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

import org.docx4j.model.datastorage.CustomXmlDataStorage;
import org.docx4j.model.datastorage.CustomXmlDataStorageImpl;
import org.docx4j.openpackaging.Base;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Parts;

/**
 * Creates a docx containing a CustomXml part.
 *
 * @author Jason Harrop
 * @version 1.0
 */
public class CreateDocxWithCustomXml {

    public static void main(String[] args) throws Exception {

        System.out.println("Creating package..");
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();

        wordMLPackage.getMainDocumentPart()
                .addStyledParagraphOfText("Title", "Hello world");

        wordMLPackage.getMainDocumentPart().addParagraphOfText("from docx4j!");


        injectCustomXmlDataStoragePart(wordMLPackage.getMainDocumentPart(),
                wordMLPackage.getParts());

        // Now save it
        wordMLPackage.save(new java.io.File(System.getProperty("user.dir") + "/customxml2.docx"));

        System.out.println("Done.");

    }


    public static void injectCustomXmlDataStoragePart(Base base, Parts parts) {

        try {
            org.docx4j.openpackaging.parts.CustomXmlDataStoragePart customXmlDataStoragePart =
                    new org.docx4j.openpackaging.parts.CustomXmlDataStoragePart(parts);
            // Defaults to /customXml/item1.xml

            CustomXmlDataStorage data = new CustomXmlDataStorageImpl();
            data.setDocument(createCustomXmlDocument());

            customXmlDataStoragePart.setData(data);

//			customXmlDataStoragePart.setDocument( createCustomXmlDocument() );

            base.addTargetPart(customXmlDataStoragePart);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public static org.w3c.dom.Document createCustomXmlDocument() {

        // TODO: implement

        return null;

    }

}
