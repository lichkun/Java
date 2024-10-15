package itstep.learning.services.form;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Singleton
public class MixedFormParseService implements FormParseService {
    @Override
    public FormParseResult parse(HttpServletRequest req) {
        final Map<String,String> formFields = new HashMap<>();
        final Map<String, FileItem> formFiles = new HashMap<>();

        //boolean isMultipart = ServletFileUpload.isMultipartContent(req); -- по документации алгоритм такой но не всегда рабочий
        String contentType = req.getHeader("Content-Type");
        boolean isMultipart = contentType.startsWith("multipart/form-data");

        if (isMultipart) {
            String charset = req.getCharacterEncoding(); //устанавливается CharsetFilter
            if(charset == null) {
                charset = "UTF-8";
            }
            try{
                for (FileItem fileItem : servletFileUpload.parseRequest(req)) {
                    if(fileItem.isFormField()){
                        formFields.put(fileItem.getFieldName(),fileItem.getString());
                    }
                    else {
                        formFiles.put(fileItem.getFieldName(),fileItem);
                    }
                }
            }
            catch (FileUploadException ex){
                logger.warning(ex.getMessage());
            }
        }
        else {
            for (Map.Entry<String, String[]> entry : req.getParameterMap().entrySet()) {
                formFields.put(entry.getKey(),entry.getValue()[0]);
            }
        }

        return new FormParseResult() {
            @Override public Map<String, String> getFields() { return formFields; }
            @Override public Map<String, FileItem> getFiles() { return formFiles; }
        };
    }

    private final static int memoryLimit = 1024 * 1024* 3; //3MB file in memory
    private final static int maxSingleFile = 1024 * 1024*2; //2MB file in memory
    private final static int maxFormSize = 1024 * 1024*5;
    private final ServletFileUpload  servletFileUpload;
    private final Logger logger;

    @Inject
    public MixedFormParseService(Logger logger) {
        this.logger = logger;
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(memoryLimit);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        this.servletFileUpload = new ServletFileUpload(factory);
        this.servletFileUpload.setFileSizeMax(maxSingleFile);
        this.servletFileUpload.setSizeMax(maxFormSize);
    }
}
