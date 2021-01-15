package com.zl.mongdb.repository;

import com.zl.mongdb.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * 通用mongodb操作dao
 * @author zhuangl
 * @version 1.0
 * @date 2020/9/24 15:10
 */
public interface ArticleRepository extends MongoRepository<Article,Long> {
    /**
     * 根据标题模糊查询
     *
     * @param title
     * @return
     */
    List<Article> findByTitleLike(String title);
}
