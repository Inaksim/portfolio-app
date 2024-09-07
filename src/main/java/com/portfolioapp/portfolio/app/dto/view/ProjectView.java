package com.portfolioapp.portfolio.app.dto.view;




import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



import java.util.List;


@AllArgsConstructor
@Getter
@Setter
public class ProjectView {
    private Long id;
    private String title;
    private String cover;
    private String username;
    private String authorAvatar;
    private Long authorId;
    private String content;
    private List<LikeView> likes;
    private boolean userHasLiked;

    public ProjectView() {

    }




}
