package com.ohgiraffers.mapping.section03.compositekey.subsection01.embeddedid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeBookService {


    private LikeRepository likeRepository;

    @Autowired
    public LikeBookService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Transactional
    public void generateLikeBook(LikeDTO likeDTO) {

        Like like = new Like(
            new LikedCompositeKey(
                    new LikedMemberNo(likeDTO.getLikedMemberNo()),
                    new LikedBookNo(likeDTO.getLikedBookNo())
            )
        );

        System.out.println("like 의 북 no : " + like.getLikeInfo().getBookNo());
        System.out.println("like 의 회원 no : " + like.getLikeInfo().getMemberNo());

        likeRepository.save(like);
    }
}
