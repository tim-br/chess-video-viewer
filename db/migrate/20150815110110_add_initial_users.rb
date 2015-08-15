class AddInitialUsers < ActiveRecord::Migration
  def change
    User.create full_name: "Jacob Stein", email: "jacobdstein@yahoo.ca", password: "jacob@$chess", password_confirmation: "jacob@$chess"
    User.create email: "TownCentreMontessori", password: "chess4you", password_confirmation: "chess4you"

  end
end
