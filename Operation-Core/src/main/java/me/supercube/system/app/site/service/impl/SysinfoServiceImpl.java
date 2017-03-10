package me.supercube.system.app.site.service.impl;

import me.supercube.system.app.site.model.Sysinfo;
import me.supercube.system.app.site.repository.SysinfoRepository;
import me.supercube.system.app.site.service.SysinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;
/**
 * 系统参数接口实现
 * Created by wangdz on 2016/9/21.
 */
@Service
public class SysinfoServiceImpl implements SysinfoService {
    @Autowired
    private SysinfoRepository sysinfoRepository;
    @Override
    public Sysinfo save(Sysinfo sysinfo) throws Exception {
        return sysinfoRepository.save(sysinfo);
    }

    @Override
    public Sysinfo findOne(String orgid) {
        return sysinfoRepository.findOne(orgid);
    }

    @Override
    public Page<Sysinfo> findAll(Map<String, String> condition, Pageable pageable) {
        return sysinfoRepository.findAll(getSpecificationWhereClause(condition), pageable);
    }
    public Specification<Sysinfo> getSpecificationWhereClause(final Map<String, String> condition) {
        return new Specification<Sysinfo>() {
            @Override
            public Predicate toPredicate(Root<Sysinfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if(condition!=null) {
                    for(Map.Entry<String, String> entry : condition.entrySet()) {
                        if(StringUtils.hasLength(entry.getKey()) && StringUtils.hasLength(entry.getValue())) {
                            predicate.getExpressions().add(cb.like(root.get(entry.getKey()), "%"+ entry.getValue() +"%"));
                        }
                    }
                }
                return predicate;
            }
        };
    }

}
