package dtos.parties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PartiesSuspenduesDto {
    private String id;
    private List<String> lesJoueurs = new ArrayList<>();
    private String etatPartie;
}
