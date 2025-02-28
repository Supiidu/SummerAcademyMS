package acc.br.summerAcademy.controller;

import acc.br.summerAcademy.model.Seller;
import acc.br.summerAcademy.service.SellerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @PostMapping
    public ResponseEntity<Seller> saveSeller(@RequestBody Seller seller){
        return ResponseEntity.ok(sellerService.create(seller));
    }
    @GetMapping
    public ResponseEntity<List<Seller>> getAllSellers(){
        return ResponseEntity.ok(sellerService.findAll());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Seller> deleteSeller(@PathVariable Long id) {
        Seller deletedSeller = sellerService.deleteSeller(id);
        return ResponseEntity.ok(deletedSeller);
    }
}
