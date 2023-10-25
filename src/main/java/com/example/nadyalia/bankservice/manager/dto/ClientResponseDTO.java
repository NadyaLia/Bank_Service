package com.example.nadyalia.bankservice.manager.dto;

import com.example.nadyalia.bankservice.client.dto.ClientInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientResponseDTO {

    private String responseCode;

    private String responseMessage;

    private ClientInfoDTO clientInfo;
}
