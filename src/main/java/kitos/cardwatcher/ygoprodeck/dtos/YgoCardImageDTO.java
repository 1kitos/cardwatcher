package kitos.cardwatcher.ygoprodeck.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YgoCardImageDTO {
 private Long id;

 @JsonProperty("image_url")
 private String imageUrl;

 @JsonProperty("image_url_small")
 private String imageUrlSmall;

 public Long getId() { return id; }
 public void setId(Long id) { this.id = id; }

 public String getImageUrl() { return imageUrl; }
 public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

 public String getImageUrlSmall() { return imageUrlSmall; }
 public void setImageUrlSmall(String imageUrlSmall) { this.imageUrlSmall = imageUrlSmall; }
}