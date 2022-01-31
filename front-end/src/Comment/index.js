import React from "react";
import { useUser } from "../UserProvider";
import jwt_decode from "jwt-decode";
const Comment = (props) => {
  const user = useUser();
  const decodedJwt = jwt_decode(user.jwt);

  const {
    id,
    emitEditComment,
    createdDate,
    createdBy,
    text,
    emitDeleteComment,
  } = props;

  console.log("decodedJWT", decodedJwt);
  console.log("createdBy", createdBy);
  return (
    <div className="comment-bubble">
      <div className="d-flex gap-5" style={{ fontWeight: "bold" }}>
        <div>{`${createdBy.name}`}</div>
        {decodedJwt.sub === createdBy.username ? (
          <>
            <div
              onClick={() => emitEditComment(id)}
              style={{ cursor: "pointer", color: "blue" }}
            >
              edit
            </div>
            <div
              onClick={() => emitDeleteComment(id)}
              style={{ cursor: "pointer", color: "red" }}
            >
              delete
            </div>
          </>
        ) : (
          <></>
        )}
      </div>
      <div>{text}</div>
    </div>
  );
};

export default Comment;
