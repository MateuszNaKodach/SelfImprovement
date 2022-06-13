class ArticlesController < ApplicationController
  def index
    @articles = Article.all
  end

  def get_by_id
    @article = Article.find(params[:id])
  end
end