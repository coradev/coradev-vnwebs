package com.coradev.vnwebs.service.impl;

import com.coradev.vnwebs.NotFoundException;
import com.coradev.vnwebs.model.Tag;
import com.coradev.vnwebs.repository.TagRepository;
import com.coradev.vnwebs.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).get();
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        //Sort sort = new Sort(Sort.Direction.DESC, "posts.size");
        Pageable pageable = PageRequest.of(0, size, Sort.Direction.DESC);
        return tagRepository.findTop(pageable);
    }

    @Override
    public List<Tag> listTag(String ids) {
        return tagRepository.findAllById(convertToString(ids));
    }

    private List<Long> convertToString(String ids){
        List<Long> list = new ArrayList<>();
        if(ids != null && !"".equals(ids)){
            String[] idArray = ids.split(",");
            for(int i = 0; i < idArray.length; i++){
                list.add(new Long(idArray[i]));
            }
        }
        return list;
    }

    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag tag1 = tagRepository.findById(id).get();
        if(tag1 == null){
            throw new NotFoundException("This tag doesn't exist");
        }
        BeanUtils.copyProperties(tag, tag1);
        return tagRepository.save(tag1);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
