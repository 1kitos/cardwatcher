package kitos.cardwatcher.ygoprodeck.dtos;

import java.util.List;

public class YgoApiResponseDTO {
 private List<YgoCardDTO> data;

 public List<YgoCardDTO> getData() { return data; }
 public void setData(List<YgoCardDTO> data) { this.data = data; }
}