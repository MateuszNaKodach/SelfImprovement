module EventsHelper

  def price(event)
    if event.free?
      'Free'
    else
      number_to_currency(event.price, precision: 0)
    end
  end

end
