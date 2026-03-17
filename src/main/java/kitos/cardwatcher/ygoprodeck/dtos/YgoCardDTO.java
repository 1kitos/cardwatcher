package kitos.cardwatcher.ygoprodeck.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class YgoCardDTO {
 private Long id;
 private String name;
 private String type;
 private String desc;
 private String race;
 private Integer atk;
 private Integer def;
 private Integer level;
 private String attribute;
 private String archetype;

 @JsonProperty("card_sets")
 private List<YgoCardSetDTO> cardSets;

 @JsonProperty("card_images")
 private List<YgoCardImageDTO> cardImages;

 public Long getId() { return id; }
 public void setId(Long id) { this.id = id; }

 public String getName() { return name; }
 public void setName(String name) { this.name = name; }

 public String getType() { return type; }
 public void setType(String type) { this.type = type; }

 public String getDesc() { return desc; }
 public void setDesc(String desc) { this.desc = desc; }

 public String getRace() { return race; }
 public void setRace(String race) { this.race = race; }

 public Integer getAtk() { return atk; }
 public void setAtk(Integer atk) { this.atk = atk; }

 public Integer getDef() { return def; }
 public void setDef(Integer def) { this.def = def; }

 public Integer getLevel() { return level; }
 public void setLevel(Integer level) { this.level = level; }

 public String getAttribute() { return attribute; }
 public void setAttribute(String attribute) { this.attribute = attribute; }

 public String getArchetype() { return archetype; }
 public void setArchetype(String archetype) { this.archetype = archetype; }

 public List<YgoCardSetDTO> getCardSets() { return cardSets; }
 public void setCardSets(List<YgoCardSetDTO> cardSets) { this.cardSets = cardSets; }

 public List<YgoCardImageDTO> getCardImages() { return cardImages; }
 public void setCardImages(List<YgoCardImageDTO> cardImages) { this.cardImages = cardImages; }
}