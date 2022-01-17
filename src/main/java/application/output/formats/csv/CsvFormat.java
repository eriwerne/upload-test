package application.output.formats.csv;


import application.output.formats.OutputFormat;
import com.opencsv.CSVWriter;
import core.article.Article;
import core.article.Materials;
import core.image.ImageOrder;
import core.order.CgiModel;
import core.order.ImageGroupOrder;
import core.order.ImageOrderGroupKey;
import core.order.Order;

import java.io.StringWriter;
import java.util.*;

public class CsvFormat implements OutputFormat {

    private static final String utf8bom = "\ufeff";
    private HashMap<String, Article> filenameArticleRelations;

    @Override
    public String getOutputFromOrder(Order order) throws Exception {
        filenameArticleRelations = order.getFilenameArticleRelations();

        StringWriter stringWriter = initStringWriter();
        CSVWriter csvWriter = initCSVWriter(stringWriter);

        csvWriter.writeNext(buildCsvHeader());

        List<ImageGroupOrder> imageGroupOrders = order.getImageGroupOrders();
        for (ImageGroupOrder imageGroupOrder : imageGroupOrders) {
            List<String[]> basicModelLines = getModelLines(imageGroupOrder.getStyleId(), imageGroupOrder.getBasicModel(), "");
            csvWriter.writeAll(basicModelLines);
            for (String function : imageGroupOrder.getFunctions()) {
                List<String[]> functionModelLines = getModelLines(imageGroupOrder.getStyleId(), imageGroupOrder.getFunctionModel(function), function);
                csvWriter.writeAll(functionModelLines);
            }
        }
        return stringWriter.toString();
    }

    private List<String[]> getModelLines(String styleId, CgiModel model, String function) throws InvalidCsvContent {
        List<String[]> lines = new ArrayList<>();
        LinkedHashMap<ImageOrderGroupKey, List<ImageOrder>> materialsImageOrderMap = model.getMaterialsImageOrderListMap().imageOrderGroups;
        for (ImageOrderGroupKey material : materialsImageOrderMap.keySet()) {
            List<ImageOrder> imageOrders = materialsImageOrderMap.get(material);
            lines.addAll(getMaterialLines(styleId, material.materials, imageOrders));
            int imageCounter = 0;
            for (ImageOrder imageOrder : imageOrders) {
                imageCounter++;
                lines.add(new String[]{
                        styleId,
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        function,
                        Integer.toString(imageCounter),
                        imageOrder.getInhaltsklassifizierung(),
                        imageOrder.getDetailfotografie().toString(),
                        imageOrder.getPerspective(),
                        imageOrder.getAufnahmeart(),
                        imageOrder.getFilenames().toString(),
                        imageOrder.getFilenamesMirror().toString()
                });
            }
        }
        checkLinesForCsvSeparator(lines);
        return lines;
    }

    private void checkLinesForCsvSeparator(List<String[]> lines) throws InvalidCsvContent {
        for (String[] line : lines)
            for (String value : line)
                if (value != null && value.contains(";"))
                    throw new InvalidCsvContent(value);
    }

    private List<String[]> getMaterialLines(String styleId, Materials materials, List<ImageOrder> imageOrders) {
        List<String[]> lines = new ArrayList<>();
        ArrayList<Article> sortedArticles = getSortedArticles(imageOrders);

        for (Article article : sortedArticles) {
            lines.add(new String[]{
                    styleId,
                    article.getImageGroup().getSizeAsString(),
                    materials.getMaterialCode(),
                    arrayAsString(materials.getFurnitureCoverMaterial()),
                    arrayAsString(materials.getFurnitureCoverColor()),
                    arrayAsString(materials.getFeetMaterial()),
                    arrayAsString(materials.getFeetColor()),
                    article.getArticleNumber(),
                    article.getName()
            });
        }
        return lines;
    }

    private String arrayAsString(String[] array) {
        if (array == null)
            return "";
        return Arrays.asList(array).toString();
    }

    private ArrayList<Article> getSortedArticles(List<ImageOrder> imageOrders) {
        HashSet<Article> articles = new HashSet<>();
        for (ImageOrder imageOrder : imageOrders)
            for (String filename : imageOrder.getFilenames())
                articles.add(filenameArticleRelations.get(filename));
        for (ImageOrder imageOrder : imageOrders)
            for (String filename : imageOrder.getFilenamesMirror())
                articles.add(filenameArticleRelations.get(filename));
        ArrayList<Article> sortedArticles = new ArrayList<>(articles);
        sortedArticles.sort(Comparator.comparing(Article::getArticleNumber));
        return sortedArticles;
    }

    private String[] buildCsvHeader() {
        return new String[]{
                "ProductComKey",
                "Größen",
                "Material",
                "Bezug",
                "Farbe",
                "Material Füße",
                "Farbe Füße",
                "Artikelnummer",
                "Artikelbezeichnung",
                "Funktionsbezeichnungen",
                "Image counter",
                "Inhaltsklassifizierung",
                "Detailfotografie",
                "Ansicht",
                "Aufnahmeart",
                "Recamiere/Ottomane links",
                "Recamiere/Ottomane rechts"
        };
    }

    private StringWriter initStringWriter() {
        StringWriter stringWriter = new StringWriter();
        stringWriter.write(utf8bom);
        return stringWriter;
    }

    private CSVWriter initCSVWriter(StringWriter stringWriter) {
        return new CSVWriter(stringWriter, ';',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
    }
}
