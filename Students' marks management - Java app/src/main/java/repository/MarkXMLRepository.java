package repository;

import domain.Mark;
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

public class MarkXMLRepository extends MarkFileRepository {

    public MarkXMLRepository(Validator<Mark> validator, String fileName) throws Exception {
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

            NodeList markNodes = root.getChildNodes();

            // de tip NodeList
            for (int i = 0; i <markNodes.getLength(); i++) {
                Node markNode = markNodes.item(i);

                if (markNode instanceof Element) {
                    markNode.getParentNode().removeChild(markNode);
                }
            }

            this.findAll().forEach(mark -> {
                root.appendChild(createMarkElement(document, mark));
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
        NodeList markNodes = root.getChildNodes();

        // de tip NodeList
        for (int i = 0; i < markNodes.getLength(); i++) {
            Node markNode = markNodes.item(i);

            if (markNode instanceof Element) {
                Mark mark = createMarkFromElement((Element) markNode);

                this.validator.validate(mark);

                this.entities.putIfAbsent(mark.getId(), mark);
            }
        }
    }

    private static Node createMarkElement(Document document, Mark mark) {
        Element markElement = document.createElement("mark");

        appendTagWithText(document, markElement, "id", String.valueOf(mark.getId()));
        appendTagWithText(document, markElement, "idlabproblem", String.valueOf(mark.getIdLabProblem()));
        appendTagWithText(document, markElement, "idstudent", String.valueOf(mark.getIdStudent()));
        appendTagWithText(document, markElement, "nota", String.valueOf(mark.getMark()));

        return markElement;
    }

    private static void appendTagWithText(Document document, Element parent, String tagName, String textContent) {
        Element element = document.createElement(tagName);

        element.setTextContent(textContent);
        parent.appendChild(element);
    }

    private static Mark createMarkFromElement(Element markElement) {
        Mark mark = new Mark();

        String id = getTextFromTag(markElement, "id");
        mark.setId(Long.parseLong(id != null ? id : "0"));

        String idLabProblem = getTextFromTag(markElement, "idlabproblem");
        mark.setIdLabProblem(Long.parseLong(idLabProblem != null ? idLabProblem : "0"));

        String idStudent = getTextFromTag(markElement, "idstudent");
        mark.setIdStudent(Long.parseLong(idStudent != null ? idStudent : "0"));

        String nota = getTextFromTag(markElement, "nota");
        mark.setMark(Integer.parseInt(nota != null ? nota : "0"));

        return mark;
    }

    private static String getTextFromTag(Element markElement, String tagName) {
        NodeList children = markElement.getElementsByTagName(tagName);

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
