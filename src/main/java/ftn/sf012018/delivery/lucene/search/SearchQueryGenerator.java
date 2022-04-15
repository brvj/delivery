package ftn.sf012018.delivery.lucene.search;

import ftn.sf012018.delivery.util.SearchType;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class SearchQueryGenerator {
    /**
     * @return match query builder with parameters
     * */
    public static QueryBuilder createMatchQueryBuilder(SimpleQueryElasticsearch simpleQueryEs) {
        if(simpleQueryEs.getValue().startsWith("\"") && simpleQueryEs.getValue().endsWith("\"")) {
            return QueryBuilderCustom.buildQuery(SearchType.PHRASE, simpleQueryEs.getField(), simpleQueryEs.getValue());
        } else {
            return QueryBuilderCustom.buildQuery(SearchType.MATCH, simpleQueryEs.getField(), simpleQueryEs.getValue());
        }
    }

    /**
     * @return term level query builder with parameters
     * */
    public static QueryBuilder createTermLevelQueryBuilder(SimpleQueryElasticsearch simpleQueryEs) {
        return QueryBuilderCustom.buildQuery(SearchType.TERM, simpleQueryEs.getField(), simpleQueryEs.getValue());
    }

    /**
     * @return range query builder with parameters
     * */
    public static QueryBuilder createRangeQueryBuilder(SimpleQueryElasticsearch simpleQueryEs) {
        return QueryBuilderCustom.buildQuery(SearchType.RANGE, simpleQueryEs.getField(), simpleQueryEs.getValue());
    }

    /**
     * @param field - field from the index that is nested object
     * @param boolQueryBuilder - created query builder (should, must, must_not or something else)
     * @param scoreMode - score mode that is related with the nested query (None, Avg, Max, Total, Min)
     * @return nested query builder with parameters
     * */
    public static QueryBuilder createNestedQueryBuilder(String field, BoolQueryBuilder boolQueryBuilder, ScoreMode scoreMode) {
        return QueryBuilders.nestedQuery(field, boolQueryBuilder, scoreMode);
    }
}
