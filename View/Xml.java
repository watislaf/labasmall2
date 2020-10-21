package View;


import java.awt.*;
import java.io.File;

import Objects.Drawable;
import Objects.SmallBall;
import Objects.SmallCube;
import containers.BackPackException;
import containers.Backpack;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.XMLConstants;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

public class Xml {
    static Point genPos(Integer n) {
        Point start_point = new Point(200, 200);
        start_point.x += 140 * n;
        return start_point;
    }

    private static boolean validate(Document document) throws Exception {
        Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(
                new File("src/../schema.xsd"));
        try {
            schema.newValidator().validate(new DOMSource(document));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void write(Backpack<Drawable> backpack, File file) throws Exception {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element root = document.createElement("backpack");
        for (Drawable sh : backpack.Data()) {
            Element shape;
            if (sh instanceof SmallCube) {
                shape = document.createElement("ball");
                shape.setAttribute("len", Integer.toString(sh.getHeight()));
            } else if (sh instanceof SmallBall) {
                shape = document.createElement("cube");
                shape.setAttribute("len", Integer.toString(sh.getHeight()));
            } else {
                throw new BackPackException("Sorry");
            }
            root.appendChild(shape);
        }
        document.appendChild(root);

        DOMSource domSource = new DOMSource(document);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        StreamResult streamResult = new StreamResult(file);
        transformer.transform(domSource, streamResult);
    }

    public static Backpack<Drawable> read(File file) throws Exception {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        if (!validate(document)) {
            throw new BackPackException("Sorry");
        }

        Backpack<Drawable> backpack = new Backpack<Drawable>(6);
        Element root = document.getDocumentElement();
        NodeList cube = root.getElementsByTagName("cube");
        NodeList ball = root.getElementsByTagName("ball");
        for (int i = 0; i < ball.getLength(); i++) {
            backpack.AddShape(new SmallBall(genPos(backpack.Size()), Integer.parseInt(((Element) ball.item(i)).getAttribute(
                    "len"))));
        }
        for (int i = 0; i < cube.getLength(); i++) {
            backpack.AddShape(new SmallCube(genPos(backpack.Size()), Integer.parseInt(((Element) ball.item(i)).getAttribute(
                    "len"))));
        }
        return backpack;
    }
}
