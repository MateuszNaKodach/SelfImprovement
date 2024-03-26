class EventsController < ApplicationController

  def index
    @events = ['BugSmash', 'Hackathon', 'Kata Camp']
  end

end
