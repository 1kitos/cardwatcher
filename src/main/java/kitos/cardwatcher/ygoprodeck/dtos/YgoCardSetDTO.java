package kitos.cardwatcher.ygoprodeck.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;

public class YgoCardSetDTO {
 @JsonProperty("set_name")
 private String setName;

 @JsonProperty("set_code")
 private String setCode;

 @JsonProperty("set_rarity")
 private String setRarity;

 @JsonProperty("set_price")
 private String setPrice;

 public String getSetName() { return setName; }
 public void setSetName(String setName) { this.setName = setName; }

 public String getSetCode() { return setCode; }
 public void setSetCode(String setCode) { this.setCode = setCode; }

 public String getSetRarity() { return setRarity; }
 public void setSetRarity(String setRarity) { this.setRarity = setRarity; }

 public String getSetPrice() { return setPrice; }
 public void setSetPrice(String setPrice) { this.setPrice = setPrice; }
}