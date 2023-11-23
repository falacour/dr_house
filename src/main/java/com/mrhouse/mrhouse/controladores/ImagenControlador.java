
import com.mrhouse.mrhouse.Entidades.Inmueble;
import com.mrhouse.mrhouse.servicios.ServicioInmueble;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {

    @Autowired
    ServicioInmueble servicioInmueble;

    @GetMapping("/inmueble/{id}")
    public ResponseEntity<byte[]> imagenUsuario(@PathVariable Long id) {
    try {
        Inmueble inmueble = servicioInmueble.getOne(id);
        byte[] imagen = inmueble.getImagen().getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    } catch (EntityNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

}
