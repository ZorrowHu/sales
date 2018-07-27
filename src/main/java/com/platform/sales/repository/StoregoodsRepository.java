package com.platform.sales.repository;

        import com.platform.sales.entity.StoreGoods;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.Query;

        import java.util.List;

public interface StoregoodsRepository extends JpaRepository<StoreGoods,Integer> {
    List<StoreGoods> findAllByStores_StoreId(Integer id);

        @Query(value = "select s from StoreGoods s where s.brandRepos.goodName like %?1%")
        List<StoreGoods> getByGoodNameLike(String keyword);

        List<StoreGoods> findAllByBrandReposTypeTypeId(Integer id);
}
