package com.proyecto.farmacia.DTOs.Auth;

public record AuthResponseDTO(String username,
                              String mensaje,
                              String token,
                              boolean status) {
}
