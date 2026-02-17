package com.financas.backend.integration.pluggy;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pluggy")
@CrossOrigin
public class PluggyController {

  private final PluggyService service;

  public PluggyController(PluggyService service) {
    this.service = service;
  }

  @GetMapping("/connect-token")
  public String token() {
    return service.createConnectToken();
  }

  @GetMapping("/accounts/{itemId}")
  public String accounts(@PathVariable String itemId) {
    return service.getAccounts(itemId);
  }

  @GetMapping("/transactions/{itemId}")
  public String transactions(@PathVariable String itemId) {
    return service.getTransactions(itemId);
  }
}