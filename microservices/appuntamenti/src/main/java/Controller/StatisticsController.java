package Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Services.AppuntamentoService;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private AppuntamentoService appuntamentoService;

    @GetMapping("/appointments")
    public ResponseEntity<Map<String, Object>> getAppuntamentiStatistics(@RequestParam String dottoreId) {
        Map<String, Object> stats = appuntamentoService.getAppuntamentiStatistics(dottoreId);
        return ResponseEntity.ok(stats);
    }
}

