package repository;

import domain.LabProblem;
import domain.validators.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class LabProblemXMLFileRepository extends LabProblemFileRepository {

    public LabProblemXMLFileRepository(Validator<LabProblem> validator, String fileName) throws Exception {
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

            NodeList labProblemNodes = root.getChildNodes();

            // de tip NodeList
            for (int i = 0; i < labProblemNodes.getLength(); i++) {
                Node labProblemNode = labProblemNodes.item(i);

                if (labProblemNode instanceof Element) {
                    labProblemNode.getParentNode().removeChild(labProblemNode);
                }
            }

            this.findAll().forEach(s -> {
                root.appendChild(createLabProblemElement(document, s));
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
        NodeList labProblemNodes = root.getChildNodes();

        // de tip NodeList
        for (int i = 0; i < labProblemNodes.getLength(); i++) {
            Node labProblemNode = labProblemNodes.item(i);

            if (labProblemNode instanceof Element) {
                LabProblem labProblem = createLabProblemFromElement((Element) labProblemNode);

                this.validator.validate(labProblem);

                this.entities.putIfAbsent(labProblem.getId(), labProblem);
//                    super.super.save();
            }
        }
    }

    private static Node createLabProblemElement(Document document, LabProblem labProblem) {
        Element labProblemElement = document.createElement("labproblem");

        appendTagWithText(document, labProblemElement, "id", String.valueOf(labProblem.getId()));
        appendTagWithText(document, labProblemElement, "discipline", labProblem.getDiscipline());
        appendTagWithText(document, labProblemElement, "labnumber", String.valueOf(labProblem.getLabNumber()));

        return labProblemElement;
    }

    private static void appendTagWithText(Document document, Element parent, String tagName, String textContent) {
        Element element = document.createElement(tagName);

        element.setTextContent(textContent);
        parent.appendChild(element);
    }

    private static LabProblem createLabProblemFromElement(Element labProblemElement) {
        LabProblem labProblem = new LabProblem();

        String id = getTextFromTag(labProblemElement, "id");
        labProblem.setId(Long.parseLong(id != null ? id : "0"));

        String discipline = getTextFromTag(labProblemElement, "discipline");
        labProblem.setDiscipline(discipline);

        String labNumber = getTextFromTag(labProblemElement, "labnumber");
        labProblem.setLabNumber(Integer.parseInt(labNumber != null ? labNumber : "0"));

        return labProblem;
    }

    private static String getTextFromTag(Element labProblemElement, String tagName) {
        NodeList children = labProblemElement.getElementsByTagName(tagName);

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
