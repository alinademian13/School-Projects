package repository;

import domain.Student;
import domain.validators.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;

public class StudentXMLFileRepository extends StudentFileRepository {

    public StudentXMLFileRepository(Validator<Student> validator, String fileName) throws Exception {
        super(validator, fileName);
    }

    @Override
    protected void saveToFile() {
        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(this.fileName);

            Element root = document.getDocumentElement();

            NodeList studentNodes = root.getChildNodes();

            for (int i = 0; i < studentNodes.getLength(); i++) {
                Node studentNode = studentNodes.item(i);

                if (studentNode instanceof Element) {
                    studentNode.getParentNode().removeChild(studentNode);
                }
            }

            this.findAll().forEach(s -> {
                root.appendChild(createStudentElement(document, s));
            });

            Transformer transformer = TransformerFactory
                    .newInstance()
                    .newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            transformer.transform(new DOMSource(document), new StreamResult(new File(this.fileName)));
        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void loadData() throws Exception {
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(this.fileName);

        Element root = document.getDocumentElement();
        NodeList studentNodes = root.getChildNodes();

        // de tip NodeList
        for (int i = 0; i < studentNodes.getLength(); i++) {
            Node studentNode = studentNodes.item(i);

            if (studentNode instanceof Element) {
                Student student = createStudentFromElement((Element) studentNode);

                this.validator.validate(student);

                this.entities.putIfAbsent(student.getId(), student);
//                    super.super.save();
            }
        }
    }

    private static Node createStudentElement(Document document, Student student) {
        Element studentElement = document.createElement("student");

        appendTagWithText(document, studentElement, "id", String.valueOf(student.getId()));
        appendTagWithText(document, studentElement, "name", student.getName());
        appendTagWithText(document, studentElement, "group", String.valueOf(student.getGroup()));

        return studentElement;
    }

    private static void appendTagWithText(Document document, Element parent, String tagName, String textContent) {
        Element element = document.createElement(tagName);

        element.setTextContent(textContent);
        parent.appendChild(element);
    }

    private static Student createStudentFromElement(Element studentElement) {

        Student student = new Student();

        String id = getTextFromTag(studentElement, "id");
        student.setId(Long.parseLong(id != null ? id : "0"));

        String name = getTextFromTag(studentElement, "name");
        student.setName(name);

        String group = getTextFromTag(studentElement, "group");
        student.setGroup(Integer.parseInt(group != null ? group : "0"));

        return student;
    }

    private static String getTextFromTag(Element studentElement, String tagName) {

        NodeList children = studentElement.getElementsByTagName(tagName);

        // de tip NodeList
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element) {
                Element element = (Element) children.item(i);
                return element.getTextContent();
            }
        }

        return null;
    }
}
