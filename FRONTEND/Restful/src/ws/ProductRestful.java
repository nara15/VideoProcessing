package ws;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.tomcat.util.http.fileupload.IOUtils;

@Path("/producto")
public class ProductRestful {
	
	/**
     * Name of the directory where uploaded files will be saved, relative to
     * the web application directory.
     */
    private static final String SAVE_DIR = "uploadFiles";
	

	@Path("/json")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("text/plain")
	public String postClichedMessage(@Context HttpServletRequest request) throws IOException, ServletException
	{
		// gets absolute path of the web application
        String appPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String savePath = appPath + File.separator + SAVE_DIR;
        
        // creates the save directory if it does not exists
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        
		for (Part part : request.getParts())
		{
			String fileName = extractFileName(part);
			String[] path = fileName.split("\\\\");
			part.write(savePath + File.separator + path[path.length - 1]);
			
		}
		
		model.TestOpenCV.run();
		
		return savePath;
	}
	
	@Path("/descarga")
	@GET
	@Produces("text/plain")
	public void downloadFile(@Context HttpServletResponse response) throws IOException 
	{
		// Dirección del video en el servidor
		String path = "C://ACS//game.avi";
		File file = new File(path);
		
		// Agrego el encabezado de las respuesta. Acá se define el tipo de dato del archivo
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	
		response.setHeader("Content-Disposition", "attachment; filename="+file.getName().replace(" ", "_"));
		
		
		// Se copia el stream hacia el stream de salida de la respuesta
		InputStream iStream = new FileInputStream(file);
		IOUtils.copy(iStream, response.getOutputStream());
		
		
		
		//response.flushBuffer();
		
		
			
	}
	
	/**
     * Extracts file name from HTTP header content-disposition
     */
    private String extractFileName(Part part) 
    {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}