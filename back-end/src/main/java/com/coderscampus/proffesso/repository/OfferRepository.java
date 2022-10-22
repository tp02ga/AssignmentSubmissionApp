package com.coderscampus.proffesso.repository;

import com.coderscampus.proffesso.domain.Offer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OfferRepository extends PagingAndSortingRepository<Offer, Long> {
//    @Query(value = "select distinct o from Offer o "
//            + " left join o.courses c "
//            + " left join c.reviews r "
//            + " left join r.user u "
//            + " left join u.authorities a "
//            + " where active = true")
//    public Page<Offer> findAllActiveOffers(Pageable pageable);
//
//    public Offer findByName(String offerName);
//
//    public Set<Offer> findByNameIn(List<String> names);
//
//    public Offer findByVideoId(String uuid);
//
//    // select * from offer where url = :offerName
//    @Query("select o from Offer o "
//            + " left join fetch o.courses c "
//            + " left join fetch c.reviews r "
//            + " left join fetch r.user u "
//            + " left join fetch u.authorities a "
//            + " left join fetch c.sections s"
//            + " left join fetch s.lessons l "
//            + " where o.url = :offerName")
//    public Offer findByUrl(@Param("offerName") String offerName);
//
//    public List<Offer> findByIdIn(List<Long> offerIds);
//
//    @Query("select distinct o from Offer o "
//            + "join o.courses as course "
//            + "join course.user as u "
//            + "where u = :user "
//            + "and o.type = :type "
//            + "order by o.name")
//    public List<Offer> findByUserAndType(@Param("user") ProffessoUser user, @Param("type") String type);
//
//    @Query(value = "select o from Offer o "
//            + " left join fetch o.courses c "
//            + " left join fetch c.reviews r "
//            + " left join fetch r.user u "
//            + " left join fetch u.authorities a "
//            + " where o.id in :ids")
//    public Set<Offer> findOffersWithCoursesAndReviews(@Param("ids") List<Long> ids);
}
