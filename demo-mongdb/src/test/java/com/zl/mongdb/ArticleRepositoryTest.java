package com.zl.mongdb;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.zl.mongdb.model.Article;
import com.zl.mongdb.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhuangl
 * @version 1.0
 * @date 2020/9/24 15:13
 */
@Slf4j
public class ArticleRepositoryTest extends DemoMongdbApplicationTests {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private Snowflake snowflake;

    @Test
    public void testSave(){
        Article article = new Article(1L,RandomUtil.randomString(20),RandomUtil.randomString(150),DateUtil.date(),DateUtil.date(),0L,0L);
        articleRepository.save(article);
        log.info("新增article:{}",JSONUtil.toJsonStr(article));
    }

    /**
     * 测试新增列表
     */
    @Test
    public void testSaveList() {
        List<Article> articles = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            articles.add(new Article(snowflake.nextId(), RandomUtil.randomString(20), RandomUtil.randomString(150), DateUtil
                    .date(), DateUtil.date(), 0L, 0L));
        }
        articleRepository.saveAll(articles);

        log.info("【articles】= {}", JSONUtil.toJsonStr(articles.stream()
                .map(Article::getId)
                .collect(Collectors.toList())));
    }

    /**
     * 测试更新
     */
    @Test
    public void testUpdate() {
        articleRepository.findById(1L).ifPresent(article -> {
            article.setTitle(article.getTitle() + "更新之后的标题");
            article.setUpdateTime(DateUtil.date());
            articleRepository.save(article);
            log.info("【article】= {}", JSONUtil.toJsonStr(article));
        });
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        // 根据主键删除
        articleRepository.deleteById(1L);

        // 全部删除
        articleRepository.deleteAll();
    }

    /**
     * 测试点赞数、访客数，使用save方式更新点赞、访客
     */
    @Test
    public void testThumbUp() {
        articleRepository.findById(1L).ifPresent(article -> {
            article.setThumbUp(article.getThumbUp() + 1);
            article.setVisits(article.getVisits() + 1);
            articleRepository.save(article);
            log.info("【标题】= {}【点赞数】= {}【访客数】= {}", article.getTitle(), article.getThumbUp(), article.getVisits());
        });
    }

    /**
     * 测试点赞数、访客数，使用更优雅/高效的方式更新点赞、访客
     */
    @Test
    public void testThumbUp2() {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(1L));
        Update update = new Update();
        update.inc("thumbUp", 1L);
        update.inc("visits", 1L);
        mongoTemplate.updateFirst(query, update, "article");

        articleRepository.findById(1L)
                .ifPresent(article -> log.info("【标题】= {}【点赞数】= {}【访客数】= {}", article.getTitle(), article.getThumbUp(), article
                        .getVisits()));
    }

    /**
     * 测试分页排序查询
     */
    @Test
    public void testQuery() {
        Sort sort = Sort.by("thumbUp", "updateTime").descending();
        PageRequest pageRequest = PageRequest.of(0, 5, sort);
        Page<Article> all = articleRepository.findAll(pageRequest);
        log.info("【总页数】= {}", all.getTotalPages());
        log.info("【总条数】= {}", all.getTotalElements());
        log.info("【当前页数据】= {}", JSONUtil.toJsonStr(all.getContent()
                .stream()
                .map(article -> "文章标题：" + article.getTitle() + "点赞数：" + article.getThumbUp() + "更新时间：" + article.getUpdateTime())
                .collect(Collectors.toList())));
    }

    /**
     * 测试根据标题模糊查询
     */
    @Test
    public void testFindByTitleLike() {
        List<Article> articles = articleRepository.findByTitleLike("更新");
        log.info("【articles】= {}", JSONUtil.toJsonStr(articles));
    }

}
