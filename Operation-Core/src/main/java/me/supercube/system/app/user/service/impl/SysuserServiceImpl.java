package me.supercube.system.app.user.service.impl;

import me.supercube.common.model.Message;
import me.supercube.common.model.Message4Collection;
import me.supercube.system.app.user.model.Sysuser;
import me.supercube.system.app.user.repository.SysuserRepository;
import me.supercube.system.app.user.service.SysuserService;
import me.supercube.security.util.PasswordHelper;
import org.apache.shiro.authc.UnknownAccountException;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chenping on 16/8/25.
 */
@Service
public class SysuserServiceImpl implements SysuserService {

    @Autowired
    private SysuserRepository sysuserRepository;

    @Override
    public Page<Sysuser> findAll(Map<String, String> condition, Pageable pageable) {
        return sysuserRepository.findAll(getWhereClause(condition), pageable);
    }


    private Specification<Sysuser> getWhereClause(final Map<String, String> condition) {
        return new Specification<Sysuser>() {
            @Override
            public Predicate toPredicate(Root<Sysuser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                Predicate predicate = cb.conjunction();

                for (String fieldName : condition.keySet()) {
                    String value = condition.get(fieldName);
                    if ("userid".equals(fieldName) && StringUtils.hasLength(value)) {
                        predicate.getExpressions().add(
                                cb.like(root.get("userid"), "%" + value + "%"));
                    }

                    if ("siteid".equals(fieldName) && StringUtils.hasLength(value)) {
                        predicate.getExpressions().add(
                                cb.equal(root.<String>get("siteid"), value));
                    }

                    if ("type".equals(fieldName) && StringUtils.hasLength(value)) {
                        predicate.getExpressions().add(
                                cb.equal(root.<String>get("type"), value));
                    }

                    if ("status".equals(fieldName) && StringUtils.hasLength(value)) {
                        predicate.getExpressions().add(
                                cb.equal(root.<String>get("status"), value));
                    }

                    if ("keyword".equals(fieldName) && StringUtils.hasLength(value)) {
                        Predicate predicate1 = cb.like(root.get("userid"), "%" + value + "%");
                        Predicate predicate2 = cb.like(root.get("loginid"), "%" + value + "%");
                        Predicate predicate3 = cb.like(root.get("truename"), "%" + value + "%");
                        Predicate predicate4 = cb.like(root.get("email"), "%" + value + "%");
                        predicate.getExpressions().add(cb.or(predicate1, predicate2, predicate3, predicate4));
                    }
                }
                return predicate;
            }
        };
    }


    @Override
    public Sysuser findOne(long id) {
        if (id <= 0) {
            return new Sysuser();
        }
        return sysuserRepository.findOne(id);
    }

//    @Override
//    public boolean userIdExists(String userid) {
//        if (StringUtils.hasLength(userid)) {
//            long count = sysuserRepository.countByUseridAndSiteid(userid, "");
//            return count > 0;
//        }
//        return false;
//    }

    @Override
    public Sysuser findByUserid(String userid) throws Exception {
        if (StringUtils.hasLength(userid)) {
            List<Sysuser> list = sysuserRepository.findByUserid(userid);
            if (list != null && list.size() > 0) {
                return list.get(0);
            }else{
                //throw new UnknownAccountException("用户为空");
                return null;
            }
        } else {
            //throw new IllegalArgumentException("用户ID不能为空");
            return null;
        }
    }

    @Override
    public Sysuser create(Sysuser entity) throws Exception {

        Sysuser existUser = findByUserid(entity.getUserid());
        if (existUser != null) {
            throw new Exception(String.format("用户ID[%s]已经存在", existUser.getUserid()));
        }
        if(!StringUtils.hasLength(entity.getPassword())) {
            throw new Exception("用户密码不能为空");
        }
        if("SYSTEM".equals(entity.getType())){
            entity.setSysuser(true);
        }else{
            entity.setSysuser(false);
        }
        PasswordHelper.newInstance().encryptPassword(entity);
        entity.setCreatedate(LocalDateTime.now());
        entity = sysuserRepository.save(entity);

        return entity;
    }

    @Override
    public Sysuser save(Sysuser entity) throws Exception {
        Sysuser existUser = findByUserid(entity.getUserid());
        if (existUser != null ) {
//            if (!StringUtils.hasLength(entity.getPassword())) {
//                entity.setPassword(existUser.getPassword());
//                entity.setSalt(existUser.getSalt());
//            } else {
//                PasswordHelper.newInstance().encryptPassword(entity);
//            }
            existUser.setTruename(entity.getTruename());
            existUser.setEmail(entity.getEmail());
            existUser.setPhone(entity.getPhone());
            existUser.setBirthdate(entity.getBirthdate());
            existUser.setCity(entity.getCity());
            existUser.setRegiondistrict(entity.getRegiondistrict());
            entity = sysuserRepository.save(existUser);
        } else {
            entity = create(entity);
        }
        return entity;
    }

    @Override
    public Sysuser changePassword(Sysuser entity) throws Exception {
        Sysuser existUser = findByUserid(entity.getUserid());
        if (existUser != null) {
            existUser.setPassword(entity.getPassword());
            PasswordHelper.newInstance().encryptPassword(existUser);
            return sysuserRepository.save(existUser);
        }
        return null;
    }

    @Override
    public Message deleteOne(long id) {
        if (id > 0) {
//            Sysuser sysuser  = sysuserRepository.findOne(id);
//            if(sysuser!=null) {
//                sysuser.setStatus("INACTIVE");
//                sysuserRepository.save(sysuser);
//            }
            //直接物理删除
            sysuserRepository.delete(id);
        }
        return null;
    }

    @Override
    public Message4Collection deleteByList(List<Sysuser> sysuserList) {

        return null;
    }

    @Override
    public Sysuser findBySysuserid(String sysuserid) throws Exception {
        if(!StringUtils.hasLength(sysuserid)){
            return null;
        }
        List<Sysuser> sysuserList = sysuserRepository.findBySysuserid(sysuserid);
        if(!sysuserList.isEmpty()){
            return sysuserList.get(0);
        }
        return null;
    }

    @Override
    public List<Sysuser> findByType(String type) throws Exception  {
        List<Sysuser> list = new ArrayList<Sysuser>();
        if (StringUtils.hasLength(type)) {
            list = sysuserRepository.findByType(type);
        } else {
            throw new IllegalArgumentException("用户类型不能为空");
        }
        return list;
    }

    @Override
    public Sysuser revertPassword(String userid,String revertPwd) throws Exception {
        List<Sysuser> sysuserList = sysuserRepository.findByUserid(userid);
        if(!sysuserList.isEmpty()){
            Sysuser sysuser = sysuserList.get(0);
            sysuser.setPassword(revertPwd);
            PasswordHelper.newInstance().encryptPassword(sysuser);
            return sysuserRepository.save(sysuser);
        }
        return null;
    }

    @Override
    public Sysuser changeUserImage(String userId, String imageUrl) throws Exception {
        Sysuser sysuser = findByUserid(userId);
        if(null != sysuser){
            //更新头像url路径
            sysuser.setImgUrl(imageUrl);
            sysuserRepository.save(sysuser);
            return sysuser;
        }
        return null;
    }

}
