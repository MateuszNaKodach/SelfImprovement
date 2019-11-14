<template>
  <div id="app">
    <h1>Welcome to VueWord blog!</h1>
    <button
      @click="toggleHighlightedPostsVisibility"
    >{{showHighlighted ? 'Hide' : 'Show'}} highlighted posts</button>
    <BlogPost v-for="blogPost in visibleBlogPosts" :post="blogPost" :key="blogPost.title"></BlogPost>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import HelloWorld from "./components/HelloWorld.vue";
import BlogPost, { Post } from "./components/BlogPost.vue";

@Component({
  components: {
    BlogPost
  }
})
export default class App extends Vue {
  public showHighlighted: boolean = true;

  private blogPosts: Post[] = [
    {
      title: "My first blogpost ever!",
      body: "Lorem ipsum dolor sit amet.",
      author: "Elke",
      datePosted: new Date(2019, 1, 18)
    },
    {
      title: "Look I am blogging!",
      body: "Hurray for me, this is my second post!",
      author: "Elke",
      datePosted: new Date(2019, 1, 19),
      highlighted: true
    },
    {
      title: "Another one?!",
      body: "Another one!",
      author: "Elke",
      datePosted: new Date(2019, 1, 20)
    }
  ];

  get visibleBlogPosts() {
    return this.blogPosts.filter(
      (post: Post) =>
        post.highlighted === undefined ||
        post.highlighted === this.showHighlighted
    );
  }

  public toggleHighlightedPostsVisibility() {
    this.showHighlighted = !this.showHighlighted;
  }

  private created() {
    this.$http.get("http://localhost:3000/blogposts").then(response => {
     this.blogPosts = response.data.map((val: any) => ({
				title: val.title,
				body: val.body,
				author: val.author,
				datePosted: new Date(val.datePosted),
				highlighted: val.highlighted,
			}));
    });
  }
}
</script>

<style lang="scss">
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
