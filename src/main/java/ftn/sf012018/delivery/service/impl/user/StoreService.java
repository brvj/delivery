package ftn.sf012018.delivery.service.impl.user;

import ftn.sf012018.delivery.lucene.search.QueryBuilderCustom;
import ftn.sf012018.delivery.mapper.user.StoreMapper;
import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import ftn.sf012018.delivery.model.mappings.user.Store;
import ftn.sf012018.delivery.repository.user.StoreRepository;
import ftn.sf012018.delivery.security.annotations.AuthorizeAdminOrCustomer;
import ftn.sf012018.delivery.security.annotations.AuthorizeAdminOrStore;
import ftn.sf012018.delivery.service.user.IStoreService;
import ftn.sf012018.delivery.util.SearchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class StoreService implements IStoreService {
    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public void index(StoreDTO storeDTO) {
        storeRepository.save(storeMapper.mapModel(storeDTO));
    }

    @Override
    @AuthorizeAdminOrStore
    public void update(StoreDTO storeNew) {
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilderCustom.buildQuery(SearchType.MATCH, "_id", storeNew.getId()))
                .build();

        SearchHits<Store> stores =
                elasticsearchRestTemplate.search(searchQuery, Store.class, IndexCoordinates.of("stores"));
        Store store = stores.getSearchHit(0).getContent();

        store.setFirstname(storeNew.getFirstname());
        store.setLastname(storeNew.getLastname());
        store.setAddress(storeNew.getAddress());
        store.setWorkingSince(storeNew.getWorkingSince());
        store.setEmail(store.getEmail());
        store.setName(storeNew.getName());

        index(storeMapper.mapToDTO(store));
    }

    @Override
    public StoreDTO getByUsernameAndPassword(String username, String password) {
        if(username.equals("") || password.equals(""))
            return null;

        return storeMapper.mapToDTO(storeRepository.findByUsernameAndPassword(username, password));
    }

    @Override
    @AuthorizeAdminOrCustomer
    public Set<StoreDTO> getAll(Pageable pageable) {
        return storeMapper.mapToDTO(storeRepository.findAll(pageable).toSet());
    }

    @Override
    public Store getByUsernameAndBlocked(String username){
        return storeRepository.findByUsernameAndBlocked(username, Boolean.FALSE);
    }
}
