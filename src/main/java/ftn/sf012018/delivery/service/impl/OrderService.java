package ftn.sf012018.delivery.service.impl;

import ftn.sf012018.delivery.lucene.search.QueryBuilderCustom;
import ftn.sf012018.delivery.lucene.search.SearchQueryGenerator;
import ftn.sf012018.delivery.lucene.search.SimpleQueryElasticsearch;
import ftn.sf012018.delivery.mapper.OrderMapper;
import ftn.sf012018.delivery.model.dto.OrderDTO;
import ftn.sf012018.delivery.model.mappings.Order;
import ftn.sf012018.delivery.model.query.OrderQueryOptions;
import ftn.sf012018.delivery.repository.OrderRepository;
import ftn.sf012018.delivery.security.annotations.AuthorizeAdminOrCustomer;
import ftn.sf012018.delivery.service.IOrderService;
import ftn.sf012018.delivery.util.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public void index(OrderDTO orderDTO) { orderRepository.save(orderMapper.mapModel(orderDTO)); }

    @Override
    @AuthorizeAdminOrCustomer
    public Set<OrderDTO> getByCustomQuery(OrderQueryOptions orderQueryOptions) {
        QueryBuilder commentQuery = SearchQueryGenerator.createMatchQueryBuilder(
                new SimpleQueryElasticsearch("_comment", orderQueryOptions.getComment()));
        QueryBuilder deliveredQuery = SearchQueryGenerator.createMatchQueryBuilder(
                new SimpleQueryElasticsearch("_delivered", String.valueOf(orderQueryOptions.isDelivered())));
        QueryBuilder priceRangeQuery = SearchQueryGenerator.createRangeQueryBuilder(
                new SimpleQueryElasticsearch("_items.article.price", String.valueOf(orderQueryOptions.getPriceStart())+"-"+
                        String.valueOf(orderQueryOptions.getPriceEnd())));
        QueryBuilder ratingRangeQuery = SearchQueryGenerator.createRangeQueryBuilder(
                new SimpleQueryElasticsearch("_rating", String.valueOf(orderQueryOptions.getRatingStart())+"-"+
                        String.valueOf(orderQueryOptions.getRatingEnd())));
        QueryBuilder customerQuery = SearchQueryGenerator.createMatchQueryBuilder(
                new SimpleQueryElasticsearch("_customer.id", orderQueryOptions.getCustomerDTO().getId()));


        BoolQueryBuilder boolQueryBuilder = QueryBuilders
                .boolQuery()
                .should(commentQuery)
                .should(deliveredQuery)
                .should(priceRangeQuery)
                .should(ratingRangeQuery)
                .should(customerQuery);

        return searchByBoolQuery(boolQueryBuilder).map( orders -> orderMapper.mapToDTO(orders.getContent())).toSet();
    }

    @Override
    @AuthorizeAdminOrCustomer
    public void commentAndRate(OrderDTO orderDTO) {
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilderCustom.buildQuery(SearchType.MATCH, "_id", orderDTO.getId()))
                .build();

        SearchHits<Order> orders =
                elasticsearchRestTemplate.search(searchQuery, Order.class, IndexCoordinates.of("orders"));
        Order order = orders.getSearchHit(0).getContent();

        if (order.getComment().equals("") && order.getRating() == 0){
            order.setComment(orderDTO.getComment());
            order.setRating(orderDTO.getRating());

            index(orderMapper.mapToDTO(order));
        }
    }

    @Override
    @AuthorizeAdminOrCustomer
    public void setOrderDelivered(OrderDTO orderDTO) {
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilderCustom.buildQuery(SearchType.MATCH, "_id", orderDTO.getId()))
                .build();

        SearchHits<Order> orders =
                elasticsearchRestTemplate.search(searchQuery, Order.class, IndexCoordinates.of("orders"));
        Order order = orders.getSearchHit(0).getContent();

        order.setDelivered(true);

        index(orderMapper.mapToDTO(order));
    }

    private SearchHits<Order> searchByBoolQuery(BoolQueryBuilder boolQueryBuilder) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();

        return elasticsearchRestTemplate.search(searchQuery, Order.class,  IndexCoordinates.of("orders"));
    }
}
