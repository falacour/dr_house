
import com.mrhouse.mrhouse.Entidades.Inmueble;
import com.mrhouse.mrhouse.servicios.ServicioInmueble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {
  @Autowired
   ServicioInmueble servicioInmueble;

    @GetMapping("/registro/{id}")
    public ResponseEntity<byte[]>imagenUsuario(@PathVariable String id){
      Inmueble inmueble=  servicioInmueble.getOne(Long.MIN_VALUE);
    byte[] imagen= inmueble.getImagen().getContenido();
       
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        
                
    return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
    
}