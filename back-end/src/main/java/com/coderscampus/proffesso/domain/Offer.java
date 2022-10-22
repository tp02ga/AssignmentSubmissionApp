package com.coderscampus.proffesso.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
@EntityListeners(AuditingEntityListener.class)
@Table(name = "offer")
public class Offer implements Serializable {
    private static final long serialVersionUID = 1385092685146377648L;
    private Long id;
    private Boolean active;
    private String name;
    private String description;
    private Double price;
    private Set<Order> orders;
    private String checkoutUrl;
    private String authorName;
    private String priceFrequency;
    private String imageUrl;
    private String warrantyUrl;
    private String billingContactEmail;
    private Integer warrantyLength;
    private Integer warrantyStart;
    private Boolean enableComments;
    private Boolean enableAttachments;
    private Boolean enableVideoDownloads;
    private String stripePlanSelected;
    @CreatedDate
    private Date createdDate;
    @CreatedBy
    private String createdBy;
    @LastModifiedDate
    private Date lastModifiedDate;
    @LastModifiedBy
    private String lastModifiedBy;
    private String embedUrl;
    private String videoId;
    private String videoProvider;
    private String url;
    private String type = "regular";
    private Integer trialDays;
    private String purchaseUrl;

    private Set<Offer> mainOffer = new HashSet<>();
    private Set<Offer> suggestedOffers = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStripePlanSelected() {
        return stripePlanSelected;
    }

    public void setStripePlanSelected(String stripePlanSelected) {
        this.stripePlanSelected = stripePlanSelected;
    }

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "offers", fetch = FetchType.LAZY)
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Column(length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Column(length = 1000)
    public String getCheckoutUrl() {
        return checkoutUrl;
    }

    public void setCheckoutUrl(String checkoutUrl) {
        this.checkoutUrl = checkoutUrl;
    }

    public String getPriceFrequency() {
        return priceFrequency;
    }

    public void setPriceFrequency(String priceFrequency) {
        this.priceFrequency = priceFrequency;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWarrantyUrl() {
        return warrantyUrl;
    }

    public void setWarrantyUrl(String warrantyUrl) {
        this.warrantyUrl = warrantyUrl;
    }

    public String getBillingContactEmail() {
        return billingContactEmail;
    }

    public void setBillingContactEmail(String billingContactEmail) {
        this.billingContactEmail = billingContactEmail;
    }

    public Integer getWarrantyLength() {
        return warrantyLength;
    }

    public void setWarrantyLength(Integer warrantyLength) {
        this.warrantyLength = warrantyLength;
    }

    public Boolean getEnableComments() {
        return enableComments;
    }

    public void setEnableComments(Boolean enableComments) {
        this.enableComments = enableComments;
    }

    public Boolean getEnableAttachments() {
        return enableAttachments;
    }

    public void setEnableAttachments(Boolean enableAttachments) {
        this.enableAttachments = enableAttachments;
    }

    public Boolean getEnableVideoDownloads() {
        return enableVideoDownloads;
    }

    public void setEnableVideoDownloads(Boolean enableVideoDownloads) {
        this.enableVideoDownloads = enableVideoDownloads;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public void setEmbedUrl(String embedUrl) {
        this.embedUrl = embedUrl;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoProvider() {
        return videoProvider;
    }

    public void setVideoProvider(String videoProvider) {
        this.videoProvider = videoProvider;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTrialDays() {
        return trialDays;
    }

    public void setTrialDays(Integer trialDays) {
        this.trialDays = trialDays;
    }

    public String getPurchaseUrl() {
        return purchaseUrl;
    }

    public void setPurchaseUrl(String purchaseUrl) {
        this.purchaseUrl = purchaseUrl;
    }

    @ManyToMany
    @JoinTable(name = "suggested_offer_mapping", joinColumns = @JoinColumn(name = "suggested_offer_id"), inverseJoinColumns = @JoinColumn(name = "main_offer_id"))
    public Set<Offer> getMainOffer() {
        return mainOffer;
    }

    public void setMainOffer(Set<Offer> mainOffer) {
        this.mainOffer = mainOffer;
    }

    @ManyToMany(mappedBy = "mainOffer")
    public Set<Offer> getSuggestedOffers() {
        return suggestedOffers;
    }

    public void setSuggestedOffers(Set<Offer> suggestedOffers) {
        this.suggestedOffers = suggestedOffers;
    }

    public Integer getWarrantyStart() {
        return warrantyStart;
    }

    public void setWarrantyStart(Integer warrantyStart) {
        this.warrantyStart = warrantyStart;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Offer other = (Offer) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Offer [id=" + id + ", name=" + name + "]";
    }

}
