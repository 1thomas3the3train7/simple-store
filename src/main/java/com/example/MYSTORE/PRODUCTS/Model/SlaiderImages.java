package com.example.MYSTORE.PRODUCTS.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
public class SlaiderImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @ManyToMany
    @JoinTable(name = "slaider_and_image",joinColumns = @JoinColumn(name = "slaider_id"),inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Collection<TeaImage> teaImages;
    public void addImage(TeaImage teaImage){
        this.teaImages.add(teaImage);
    }
    public SlaiderImages(){}
}
