import dayjs from "dayjs";
import React, { useEffect, useState } from "react";
import { Button } from "react-bootstrap";
import Comment from "../Comment";
import ajax from "../Services/fetchService";
import { useUser } from "../UserProvider";
import { useInterval } from "../util/useInterval";

const CommentContainer = (props) => {
  const { assignmentId } = props;
  const user = useUser();

  const emptyComment = {
    id: null,
    text: "",
    assignmentId: assignmentId != null ? parseInt(assignmentId) : null,
    user: user.jwt,
    createdDate: null,
  };

  const [comment, setComment] = useState(emptyComment);
  const [comments, setComments] = useState([]);

  useInterval(() => {
    updateCommentTimeDisplay();
  }, 1000 * 5);
  function updateCommentTimeDisplay() {
    const commentsCopy = [...comments];
    commentsCopy.forEach(
      (comment) => (comment.createdDate = dayjs(comment.createdDate))
    );
    formatComments(commentsCopy);
  }

  function handleEditComment(commentId) {
    const i = comments.findIndex((comment) => comment.id === commentId);
    const commentCopy = {
      id: comments[i].id,
      text: comments[i].text,
      assignmentId: assignmentId != null ? parseInt(assignmentId) : null,
      user: user.jwt,
      createdDate: comments[i].createdDate,
    };
    setComment(commentCopy);
  }

  useEffect(() => {
    console.log(comment);
  }, [comment]);

  function handleDeleteComment(commentId) {
    // TODO: send DELETE request to server
    ajax(`/api/comments/${commentId}`, "delete", user.jwt).then((msg) => {
      const commentsCopy = [...comments];
      const i = commentsCopy.findIndex((comment) => comment.id === commentId);
      commentsCopy.splice(i, 1);
      formatComments(commentsCopy);
    });
  }
  function formatComments(commentsCopy) {
    commentsCopy.forEach((comment) => {
      if (typeof comment.createDate === "string") {
        console.log(
          "BEFORE Converting string date to dayjs date",
          comment.createdDate
        );
        comment.createDate = dayjs(comment.createDate);
        console.log(
          "AFTER Converting string date to dayjs date",
          comment.createdDate
        );
      }
    });
    setComments(commentsCopy);
  }

  useEffect(() => {
    ajax(
      `/api/comments?assignmentId=${assignmentId}`,
      "get",
      user.jwt,
      null
    ).then((commentsData) => {
      formatComments(commentsData);
    });
  }, []);

  function updateComment(value) {
    const commentCopy = { ...comment };
    commentCopy.text = value;
    setComment(commentCopy);
  }
  function submitComment() {
    // if (
    //   typeof comment.createdDate === "object" &&
    //   comment.createdDate != null
    // ) {
    //   comment.createdDate = comment.createdDate.toDate();
    // }
    if (comment.id) {
      ajax(`/api/comments/${comment.id}`, "put", user.jwt, comment).then(
        (d) => {
          const commentsCopy = [...comments];
          const i = commentsCopy.findIndex((comment) => comment.id === d.id);
          commentsCopy[i] = d;
          formatComments(commentsCopy);

          setComment(emptyComment);
        }
      );
    } else {
      ajax("/api/comments", "post", user.jwt, comment).then((d) => {
        const commentsCopy = [...comments];
        commentsCopy.push(d);
        formatComments(commentsCopy);
        setComment(emptyComment);
      });
    }
  }
  return (
    <>
      <div className="mt-5">
        <textarea
          style={{ width: "100%", borderRadius: "0.25em" }}
          onChange={(e) => updateComment(e.target.value)}
          value={comment.text}
        ></textarea>
        <Button onClick={() => submitComment()}>Post Comment</Button>
      </div>
      <div className="mt-5">
        {comments.map((comment) => (
          <Comment
            key={comment.id}
            commentData={comment}
            emitDeleteComment={handleDeleteComment}
            emitEditComment={handleEditComment}
          />
        ))}
      </div>
    </>
  );
};

export default CommentContainer;
