package application.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContainerIdExtractorForCSV implements ContainerIdExtractor {
    private final String csv;

    public ContainerIdExtractorForCSV(String csv) {
        this.csv = csv;
    }

    @Override
    public List<String> readContainerIdsInObject() {
        Pattern p = Pattern.compile("\\d*.jpg");
        Matcher m = p.matcher(String.valueOf(csv));

        ArrayList<String> containerIds = new ArrayList<>();
        while (m.find())
            containerIds.add(m.group());
        System.out.println(containerIds);
        return containerIds;
    }
}
