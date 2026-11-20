package plataforma;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication(scanBasePackages = {"br.edu.plataforma", "plataforma"}) public class PlataformaApplication { public static void main(String[] args) { SpringApplication.run(PlataformaApplication.class,args); } }
