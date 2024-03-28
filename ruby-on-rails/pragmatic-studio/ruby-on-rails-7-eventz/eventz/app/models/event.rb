class Event < ApplicationRecord

  def free?
    price.blank? || price.zero?
  end

end
