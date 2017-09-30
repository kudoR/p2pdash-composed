package eu.ffs.controller;


import eu.ffs.repository.CentralRepositoryService;
import eu.ffs.repository.ViventorAccountEntryRepository;
import eu.ffs.repository.entity.MintosAccountEntry;
import eu.ffs.repository.entity.ViventorAccountEntry;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ViventorDataController extends BaseDataController {

    @Autowired
    ViventorAccountEntryRepository repository;

    @Autowired
    private CentralRepositoryService centralRepositoryService;

    @RequestMapping("/findAll/viventor")
    public Iterable<ViventorAccountEntry> getAll() {
        return repository.findAll();
    }


    @PostMapping("/uploadViventor")
    public ModelAndView handleViventorFileUpload(@RequestParam("file") MultipartFile file) throws Exception {

        PoiItemReader<MintosAccountEntry> reader = new PoiItemReader<>();

        byte[] bytes = file.getBytes();
        ByteArrayResource byteArrayResource = new ByteArrayResource(bytes);

        reader.setLinesToSkip(8);
        reader.setResource(byteArrayResource);
        reader.setRowMapper(genericRowMapper());

        reader.afterPropertiesSet();
        reader.open(new ExecutionContext());

        for (MintosAccountEntry read = reader.read(); read != null; read = reader.read()) {
            centralRepositoryService.saveAccountEntry(read);
        }

        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/getViventorReport")
    public Report getReport() {
        return getReportFromRepo(this.repository);
    }

    @Override
    public Class getTargetType() {
        return ViventorAccountEntry.class;
    }
}
