package Controllers;

import Model.CsvManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class CsvDownloadController {

    private CsvManager csvManager;

    public CsvDownloadController(){this.csvManager = new CsvManager();}

    //TODO Can this logic be moved?
    @RequestMapping(value = "ETF/{etfSymbol}/downloadCsv")
    public void downloadCsv(@PathVariable String etfSymbol, HttpServletResponse response) throws IOException{
    }
}