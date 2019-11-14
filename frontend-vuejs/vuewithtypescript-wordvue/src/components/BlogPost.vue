<template>
  <div class="blogpost" :class="{highlighted: post.highlighted}">
    <h2>{{post.title}}</h2>
    <p v-if="post.highlighted">This post is highlighted</p>
    <p>{{post.body}}</p>
    <p class="meta">Written by {{post.author}} on {{date}}</p>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";

export interface Post {
  title: string;
  body: string;
  author: string;
  datePosted: Date;
  highlighted?: boolean;
}

@Component
export default class BlogPost extends Vue {
  @Prop()
  private post!: Post;

  get date(): string {
    const date = this.post.datePosted.getDate();
    const month = this.post.datePosted.getMonth();
    const year = this.post.datePosted.getFullYear();
    return `${date}/${month}/${year}`;
  }
}
</script>
<style scoped lang="scss">
div.blogpost {
  width: 400px;
  margin: 0 auto;
  &.highlighted {
    border: 1px solid #f4d942;
    background: #fff3b2;
  }
  h2 {
    text-decoration: underline;
  }
  p.meta {
    font-style: italic;
  }
}
</style>