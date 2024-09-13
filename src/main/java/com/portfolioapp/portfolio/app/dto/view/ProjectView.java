package com.portfolioapp.portfolio.app.dto.view;




import com.portfolioapp.portfolio.app.enitity.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



import java.util.List;
import java.util.stream.Collectors;


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
    public ProjectView(Project project) {
        this.id = project.getId();
        this.title = project.getTitle();
        this.cover = project.getCover();
        this.username = project.getUser().getUsername();
        this.authorId = project.getUser().getId();
        this.content = project.getContent();
        this.likes = project.getLikes().stream().map(LikeView::new).collect(Collectors.toList());
        this.userHasLiked = false;
    }

}
