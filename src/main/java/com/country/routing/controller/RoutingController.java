package com.country.routing.controller;

import com.country.routing.model.Route;
import com.country.routing.service.RoutingService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RoutingController {

    private final RoutingService routingService;

    public RoutingController(RoutingService routingService) {
        this.routingService = routingService;
    }

    @GetMapping("/route/{origin}/{destination}")
    public ResponseEntity<Route> getRoute(@PathVariable final String origin,
                                          @PathVariable final String destination) {

        return ResponseEntity.ok(new Route(routingService.findRoute(origin, destination)));
    }

}
